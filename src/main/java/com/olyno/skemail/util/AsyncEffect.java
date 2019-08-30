package com.olyno.skemail.util;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.Delay;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.variables.Variables;
import com.olyno.skemail.SkEmail;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.concurrent.CompletableFuture;

/**
 * This is *not* similar to Skript's AsyncEffect class.
 * This just provides some utility for local variable stuff.
 * Only the delay stuff works if the Variables methods aren't available.
 *
 * @author Blueyescat <https://github.com/Blueyescat>
 */
public abstract class AsyncEffect extends Effect {

    private static boolean CAN_MANAGE_LOCAL_VARS = Skript.methodExists(Variables.class, "removeLocals", Event.class) &&
            Skript.methodExists(Variables.class, "setLocalVariables", Event.class, Object.class);

    private Object localVars;

    @Override
    protected TriggerItem walk(Event e) {
        debug(e, true);
        Delay.addDelayedEvent(e);
        execute(e);
        return null;
    }

    protected void executeCode(Event e, Runnable asyncCode) {
        executeCode(e, asyncCode, () -> {
        });
    }

    protected void executeCode(Event e, Runnable asyncCode, Runnable syncCode) {
        removeAndSaveLocals(e);
        CompletableFuture.supplyAsync(() -> {
            asyncCode.run();
            return true;
        }).whenComplete((result, ex) -> Bukkit.getScheduler().runTask(SkEmail.getInstance(), () -> {
            if (ex != null) {
                putLocalsBackAndContinueTrigger(e);
                Skript.exception(ex);
                return;
            }
            if (!result) {
                putLocalsBackAndContinueTrigger(e);
                return;
            }
            syncCode.run();
            if (shouldContinue()) {
                putLocalsBack(e);
                continueTriggerAndRemoveLocals(e);
            }
        }));
    }

    protected void removeAndSaveLocals(Event e) {
        if (CAN_MANAGE_LOCAL_VARS)
            localVars = Variables.removeLocals(e);
    }

    protected void putLocalsBack(Event e) {
        if (CAN_MANAGE_LOCAL_VARS && localVars != null)
            Variables.setLocalVariables(e, localVars);
    }

    protected boolean shouldContinue() {
        return getNext() != null;
    }

    protected void continueTrigger(Event e) {
        TriggerItem.walk(getNext(), e);
    }

    protected void continueTriggerAndRemoveLocals(Event e) {
        continueTrigger(e);
        if (CAN_MANAGE_LOCAL_VARS)
            Variables.removeLocals(e);
    }

    protected void putLocalsBackAndContinueTrigger(Event e) {
        if (CAN_MANAGE_LOCAL_VARS)
            putLocalsBack(e);
        continueTrigger(e);
    }

}
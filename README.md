![](https://travis-ci.org/AlexLew95/SkEmail.svg?branch=master)
# SkEmail
![Banner at this link: https://i.imgur.com/tO9GwwW.png](https://i.imgur.com/tO9GwwW.png)

[Documentation](https://skripthub.net/docs/?addon=SkEmail) | [Source code](https://github.com/AlexLew95/SkEmail) | [Downloads](https://github.com/AlexLew95/SkEmail/releases) | [Any problem, suggestion, or issue? Say me here](https://github.com/AlexLew95/SkEmail/issues/new?template=bug_report.md)

**__Disclaimer:__**

I'm in no way responsible for your actions with my tool.
This ressource will not work correctly if:
- Your gmail account (or other service account) has double factor enable
- You didn't enable "Allow less secure apps"
Think to follow this tuto to be sure you forgot nothing: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail
I will only help you with the last version, and not older version. Think to update it.

**__Usage example:__**

```python
on load:
    login to gmail service from mail "myemail@gmail.com" and password "my password"
 
command send:
    trigger:
        make new email:
            set object of email to "Test email"
            set body of email to "Look my beautiful email my friend!"
            set receiver of email to "myfriend@gmail.com"
            add "plugins/myfile.txt" to attachments of email
        send last email
     
command read:
    trigger:
        set {_mails::*} to last 2 mails
        broadcast author of {_mails::1}
 
command service add <text>:
    trigger:
        make new mail service:
            set name of mail service to arg-1
            set smtp address of mail service to "smtp.myservice.com"
            set smtp port of mail service to 465
            set imap address of mail service to "imap.myservice.com"
            set imap port of mail service to 900
        set {myservice::%arg-1%} to last mail service
```

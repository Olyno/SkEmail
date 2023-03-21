![Banner at this link: https://i.imgur.com/tO9GwwW.png](https://i.imgur.com/tO9GwwW.png)

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)

[![](https://img.shields.io/github/issues/Olyno/SkEmail.svg?style=for-the-badge)](https://github.com/Olyno/SkEmail/issues) ![](https://img.shields.io/github/forks/Olyno/SkEmail.svg?style=for-the-badge) ![](https://img.shields.io/github/stars/Olyno/SkEmail.svg?style=for-the-badge)

[Documentation](https://skripthub.net/docs/?addon=SkEmail) | [Source code](https://github.com/Olyno/SkEmail) | [Downloads](https://github.com/Olyno/SkEmail/releases) | [Any problem, suggestion, or issue? Say me here](https://github.com/Olyno/SkEmail/issues/new?template=bug_report.md)

## Disclaimer

I'm in no way responsible for your actions with my tool.
This ressource will not work for Gmail accounts if you don't use an [App Password](https://support.google.com/mail/answer/185833?hl=en) instead of your account password.

## Usage example

```vb
on load:
    login to gmail service from mail "myemail@gmail.com" and password "my password"
 
command send:
    trigger:
        make new email:
            set object of email to "Test email"
            set body of email to "Look my beautiful email my friend!"
            set receiver of email to "myfriend@gmail.com"
            add "plugins/myfile.txt" to attachments of email
        send last email created
     
command read:
    trigger:
        set {_mails::*} to last 2 mails
        broadcast author of {_mails::1}
```

## Register new mail service

To register a service with SkEmail 1.5+, a ``services.yml`` file is available in ``plugins/SkEmail``. Just follow the following pattern:

```yaml
IdOfYourService:
    name: name of your service
    smtp_address: smtp.yourservice.com
    smtp_port: 465
    imap_address: imap.yourservice.com
    imap_port: 993
```

> **Note:** Since SkEmail 2.0, the addon is using ``STARTTLS`` encryption instead of SSL/TLS. The most part of smtp servers support this encryption method with the smtp port 587. Don't forget to do this change, or see with your provider how it is managed.

The name of your service will be used as pattern. With the example above:
```
login to name of your service service from mail "youremail@gmail.com" and password "your password"
```

If your mail service seems good, you will get the message ``[SkEmail] Service named "name of your service" has been loaded!``.

To reload SkEmail or services, you needn't reload all your server, you can use ``/skemail reload``, it's enough.

## Default services

SkEmail supports some providers by default. Note that i personnaly don't use all of them, so if you have any problem with one of them, please open an issue. Here is the list of the default services:

- Gmail
- Yahoo
- Outlook (Hotmail)
- Zoho
- GMX
- NTL
- AOL

## BStats/Metrics

SkEmail contains metrics for stats, available here:
https://bstats.org/plugin/bukkit/SkEmail

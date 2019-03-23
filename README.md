![Banner at this link: https://i.imgur.com/tO9GwwW.png](https://i.imgur.com/tO9GwwW.png)

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)

[![](https://img.shields.io/github/issues/AlexLew95/SkEmail.svg?style=for-the-badge)](https://github.com/AlexLew95/SkEmail/issues) ![](https://img.shields.io/github/forks/AlexLew95/SkEmail.svg?style=for-the-badge) ![](https://img.shields.io/github/stars/AlexLew95/SkEmail.svg?style=for-the-badge)
[Documentation](https://skripthub.net/docs/?addon=SkEmail) | [Source code](https://github.com/AlexLew95/SkEmail) | [Downloads](https://github.com/AlexLew95/SkEmail/releases) | [Any problem, suggestion, or issue? Say me here](https://github.com/AlexLew95/SkEmail/issues/new?template=bug_report.md)

## Disclaimer

I'm in no way responsible for your actions with my tool.
This ressource will not work correctly if:
- Your gmail account (or other service account) has double factor enable
- You didn't enable "Allow less secure apps"
Think to follow this tuto to be sure you forgot nothing: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail
I will only help you with the last version, and not older version. Think to update it.

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
        send last email
     
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

The name of your service will be used as pattern. With the example above:
```
login to name of your service service from mail "youremail@gmail.com" and password "your password"
```

If your mail service seems good, you will get the message ``[SkEmail] Service named "name of your service" has been loaded!``.

To reload SkEmail or services, you needn't reload all your server, you can use ``/skemail reload``, it's enough.
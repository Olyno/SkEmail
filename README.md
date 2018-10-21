# SkEmail
Skript addon to send emails

First all, configure your gmail account to allow SkEmail to send mails: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail

**Usage:**
```vb
on load:
	connection to "myemail@gmail.com" with "my password"

command test:
	trigger:
		make new email:
			set object of email to "Test email"
			set body of email to "Look my beautiful email my friend!"
			set receiver of email to "myfriend@gmail.com"
		send last email
```

Mails services available:

 - gmail
 - hotmail
 - yahoo
 - live
 - outlook
 
 Create an issue if you want another service available!

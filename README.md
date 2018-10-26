# SkEmail
Skript addon to send emails

First all, configure your gmail account to allow SkEmail to send mails: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail

**Usage SkEmail <= 1.1:**
```vb
on load:
	connection to "myemail@gmail.com" with "my password"

command send:
	trigger:
		make new email:
			set object of email to "Test email"
			set body of email to "Look my beautiful email my friend!"
			set receiver of email to "myfriend@gmail.com"
		send last email
```
**Usage SkEmail 1.2+:**
```vb
on load:
	connection to mails

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

All mails services are available. To change it, configure the file "plugins/SkEmail/config.json"

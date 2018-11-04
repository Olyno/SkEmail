# SkEmail
Skript addon to send emails

First all, configure your gmail account to allow SkEmail to send mails: https://github.com/AlexLew95/SkEmail/wiki/Configure-your-email-address-for-SkEmail

**Usage SkEmail <= 1.1:**
```vb
on load:
	connect to "myemail@gmail.com" with "my password"

command send:
	trigger:
		make new email:
			set object of email to "Test email"
			set body of email to "Look my beautiful email my friend!"
			set receiver of email to "myfriend@gmail.com"
		send last email
```
**Usage SkEmail 1.2:**
```vb
on load:
	connect to mails

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

**Usage SkEmail 1.3:**
```vb
on load:
	connect to "myemail@gmail.com" with "my password"

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

# SkEmail
Skript addon for send emails

```vb
on skript load:
	connexion to "myemail@gmail.com" with "my password"

command test:
	trigger:
		make new email:
			set object of email to "Test email"
			set body of email to "Look my beautiful email my friend!"
			set receiver of email to "myfriend@gmail.com"
		send last email
```

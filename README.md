# SkEmail
Skript addon for send emails

```vb
command test:
  trigger:
    make new email:
      set object of email to "test"
      set from part of email to "myemail@gmail.com"
      set body of email to "blabla that's a test"
     send last email
```

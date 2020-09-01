# automationpractice.com

[automationpractice.com](http://automationpractice.com/index.php "Automation Practice Website") is an end-end e-commerce website. It covers the complete online shopping workflow. So there is lot of back and forth between server and client, lots of validations built into it to give you a complete experience to practice the scenarios you might find in real time projects.

Tools:

• Selenium Web Driver

• Java

• TestNG


Tested functionalities:

• Create new account form

• Login form

• Complete shopping workflow

UI Automated test

- web application URL :   http://automationpractice.com/index.php;
This is the test automation task in which below test scenarios with test cases should be included:
 Scenario 1 : Login Test ● Go to the home page. ● Click Sign in button. ● Fill Email address in Already registered block. ● Fill Password in Already registered block. ● Click on Sign in button. ● Verify User details. ● Verify details on home page after successful login. 
Scenario 2 : User Registration ● Go to the home page. ● Click Sign in button. ● Fill Email address to create an account. ● Fill all fields with correct data. ● Click Register button. ● Verify User details. ● Verify details on home page after successful login. 
Scenario 3 : Checkout ● Go to the home page. ● Click Sign in button. ● Fill Email address in Already registered block. ● Fill Password in Already registered block. ● Click on Sign in button. ● Verify User details. ● Verify details on home page after successful login. ● Click Women button in the header. ● Click the product. ● Click on Add to cart. ● Click Proceed to checkout. ● Click Proceed to checkout from Summary. ● Click Proceed to checkout from address. ● Click by Terms of service to agree. ● Click Proceed to checkout from shipping. ● Select the payment method. ● Click on I confirm my order. ● Verify details on final confirmation page.


How to Generate the Report of execution :  
allure generate --clean /Users/laddu/IdeaProjects/automationPractice/allure-results && allure open

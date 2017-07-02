<!DOCTYPE html>
<html>
    <head>
        <name>${name!""}</name>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>${book!""}</div>
        <br>
        
        <#list prices as price>
        <div>${(price)!""}</div><br>
        </#list>
    </body>
</html>
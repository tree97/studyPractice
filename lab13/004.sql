select * from messages where user_id = 2 and
( text like '% hello %'
or text like '%,hello %'
or text like '%.hello %'
or text like '%!hello %'
or text like '%?hello %'
or text like '% hello,%'
or text like '% hello!%'
or text like '% hello.%'
or text like '% hello?%'
or text like 'hello %'
or text like 'hello,%'
or text like 'hello!%'
or text like 'hello?%' );
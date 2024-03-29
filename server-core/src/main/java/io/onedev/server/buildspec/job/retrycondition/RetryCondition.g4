grammar RetryCondition;

condition
    : WS* (criteria|Never) WS* EOF
    ;

criteria
    : criteriaField=Quoted WS+ operator=(IsEmpty|IsNotEmpty) #FieldOperatorCriteria
    | criteriaField=Quoted WS+ operator=(Contains|Is|IsNot) WS+ criteriaValue=Quoted #FieldOperatorValueCriteria
    | criteria WS+ And WS+ criteria	#AndCriteria
    | criteria WS+ Or WS+ criteria #OrCriteria
    | Not WS* LParens WS* criteria WS* RParens #NotCriteria
    | LParens WS* criteria WS* RParens #ParensCriteria
    ;
	
Never
	: 'never'
	;
	
Contains
	: 'contains'
	;
	
Is
	: 'is'
	;	

IsNot
    : 'is' WS+ 'not'
    ;

IsEmpty
	: 'is' WS+ 'empty'
	;	

IsNotEmpty
    : 'is' WS+ 'not' WS+ 'empty'
    ;

And
	: 'and'
	;

Or
	: 'or'
	;

Not
	: 'not'
	;

LParens
	: '('
	;

RParens
	: ')'
	;

Quoted
    : '"' ('\\'.|~[\\"])+? '"'
    ;

WS
    : ' '
    ;

Identifier
	: [a-zA-Z0-9:_/\\+\-;]+
	;

# N-Lizards
Place p baby lizards in an nxn matrix such that all are safe

For	each	baby	lizard,	you	can	place	them	in	one	spot	on	a	grid.	From	there,	they	can	shoot	out	their	tongue	up,	down,	left,	right	and	diagonally	as	well.	Their	tongues	are	very	long	and	can	reach	to	the	edge	of	the	nursery	from	any	location.

In	addition	to	baby	lizards,	the	nursery	may	have	some	trees	planted	in	it.	The	lizards	cannot shoot	their	tongues	through	the	trees	nor	can	you	move	a	lizard	into	the	same	place	as	a	tree.	As such,	a	tree	will	block	any	lizard	from	eating	another	lizard	if	it	is	in	the	path.	Additionally,	the	tree blocks you	from	moving	the	lizard	to	that	location.	

Write	a	program	that	will	take	an	input	file	that	has	an	arrangement	of	trees	and	will	output	a	new	arrangement	of	lizards such	that	no	baby	lizard	can	eat	another	one.	

The following algorithms need to be used to find the solution:
- Breadth-first	search	(BFS)	
- Depth-first	search	(DFS)	
- Simulated	annealing	(SA).

Input:	The	file	input.txt	in	the	current	directory	of the program	will	be	formatted	as	follows:	
	
First	line:		   instruction	of	which	algorithm	to	use:	BFS,	DFS	or	SA	
Second	line:		 strictly	positive	32-bit	integer	n,	the	width	and	height	of	the	square	nursery	
Third	line:		   strictly	positive	32-bit	integer	p,	the	number	of	baby	lizards	
Next	n	lines:		 the	n	x	n	nursery,	one	file	line	per	nursery	row	.	
It	will	have	a	0	where	there	is	nothing,	and	a	2	where	there	is	a	tree.		
	
So	for	instance,	using	the	DFS	algorithm	to	place	8	lizards	in	the	8x8	nursery	would	look	like:	

DFS  
8  
8  
00000000  
00000000  
00000000  
00002000  
00000000  
00000200  
00000000  
00000000  

Output:	The	file	output.txt which the	program	creates	in	the	current	directory	will be formatted	as	follows:	
	
First	line:		OK	or	FAIL,	indicating	whether	a	solution	was	found	or	not. If	FAIL,	any	following	lines	are	ignored.	
Next	n	lines:		 the	n	x	n	nursery,	one	line	in	the	file	per	nursery	row,	including	the	baby	lizards	and	trees.	It	will	have	a	0	where	there	is	nothing,	a	1	where	a	baby lizard was placed,	and	a	2	where	there	is	a	tree.

OK  
00000100  
10000000  
00001000  
01002001  
00000000  
00100200  
00000010  
00010000  

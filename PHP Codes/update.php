<?php
	$con = mysqli_connect('localhost', 'root', '', 'wallet');

	if (mysqli_connect_errno())
	{
		echo "1";//Connection Failed
		exit();
	}

	$newamount = $_GET['amount'];
	$username = $_GET['name'];

	$namecheckquery = "SELECT * FROM users WHERE username='" . $username . "';";

	$namecheck = mysqli_query($con, $namecheckquery) or die("2");// Name check query failed

	if (mysqli_num_rows($namecheck) != 1)
	{
		echo "5"; //Either no user with name, or more than one
		exit();
	}

	$existinginfo = mysqli_fetch_assoc($namecheck);
	$lastcash = $existinginfo["amount"];
	
	$updatequery = "UPDATE users SET amount =". $lastcash . " + " . $newamount . " WHERE username = '" . $username . "';";
	mysqli_query($con, $updatequery) or die("7"); //error code #7 - UPDATE query failed

?>
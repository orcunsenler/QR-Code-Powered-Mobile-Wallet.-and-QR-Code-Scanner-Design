<?php
	$con = mysqli_connect('localhost', 'root', '', 'wallet');

	if (mysqli_connect_errno())
	{
		echo "1";//Connection Failed
		exit();
	}
	
	$username = $_GET['username'];
	$password = $_GET['password'];

	$namecheckquery = "SELECT * FROM users WHERE username='" . $username . "';";

	$namecheck = mysqli_query($con, $namecheckquery) or die("2");// Name check query failed

	if (mysqli_num_rows($namecheck) != 1)
	{
		echo "5"; //Either no user with name, or more than one
		exit();
	}

	$existinginfo = mysqli_fetch_assoc($namecheck);
	$loginpassword = $existinginfo["password"];

	if ($password != $loginpassword)
	{
		echo "6"; //error code #6 - password doesn't match table - Incorrect Password
		exit();
	}

	echo "0"; // . $existinginfo["cash"];

?>
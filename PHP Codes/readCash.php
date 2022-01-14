<?php
	$con = mysqli_connect('localhost', 'root', '', 'wallet');

	if (mysqli_connect_errno())
	{
		echo "1";//Connection Failed
		exit();
	}
	
	$username = $_GET['username'];

	$namecheckquery = "SELECT * FROM users WHERE username='" . $username . "';";

	$namecheck = mysqli_query($con, $namecheckquery) or die("2");// Name check query failed

	if (mysqli_num_rows($namecheck) >0)
	{
		while($row = mysqli_fetch_assoc($namecheck)){
			echo   json_encode($row);
		}
	} else{
		echo "5"; //Either no user with name, or more than one
		exit();
	}

?>



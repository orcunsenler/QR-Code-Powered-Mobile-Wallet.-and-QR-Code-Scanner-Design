<?php
	$con = mysqli_connect('localhost', 'root', '', 'wallet');

	if (mysqli_connect_errno())
	{
		echo "1";//Connection Failed
		exit();
	}
	
	$encrypted = $_GET['encrypted'];

	$encryptedcheckquery = "SELECT * FROM users WHERE encrypted='" . $encrypted . "';";

	$encryptedcheck = mysqli_query($con, $encryptedcheckquery) or die("2");// encrypted check query failed
/*
	if (mysqli_num_rows($encryptedcheck) >0)
	{
		while($row = mysqli_fetch_assoc($encryptedcheck)){
			echo   json_encode($row);
		}
	} else{
		echo "5"; //Either no user with encrypted, or more than one
		exit();
	}
*/	
	$existinginfo = mysqli_fetch_assoc($encryptedcheck);
	$loginencrypted = $existinginfo["encrypted"];
	if($encrypted==$loginencrypted){
		echo $existinginfo["username"] ."|". $existinginfo["amount"];
	}

?>


<?php

require "DataBaseConfig.php";

class DataBase
{
	public $connect;
	public $data;
	private $sql;
	protected $servername;
	protected $username;
	protected $password;
	protected $databasename;

	public function __construct()
	{
		$this->connect = null;
		$this->data = null;
		$this->sql = null;
		$dbc = new DataBaseConfig();
		$this->servername = $dbc->servername;
		$this->username = $dbc->username;
		$this->password = $dbc->password;
		$this->databasename = $dbc->databasename;
	}

	function dbConnect()
	{
		$this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
		return $this->connect;
	}

	function prepareData($data)
	{
		return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
	}

	function logIn($table, $username, $password)
	{
		$username = $this->prepareData($username);
		$password = $this->prepareData($password);
		$this->sql = "SELECT * FROM " . $table . " WHERE username = '" . $username . "'";
		$result = mysqli_query($this->connect, $this->sql);
		$row = mysqli_fetch_assoc($result);
		if (mysqli_num_rows($result) != 0) {
			$dbusername = $row['username'];
			$dbpassword = $row['password'];
			if ($dbusername == $username && $password == $dbpassword) {
				$login = true;
			} else $login = false;
		} else $login = false;

		return $login;
	}

	function signUp($table, $fullname, $email, $username, $password)
	{
		$fullname = $this->prepareData($fullname);
		$username = $this->prepareData($username);
		$password = $this->prepareData($password);
		$email = $this->prepareData($email);
		$this->sql = "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
		if (mysqli_query($this->connect, $this->sql)) {
			return true;
		} else return false;
	}
	
	function readDet($table, $username)
	{
		$username = $this->prepareData($username);
		$this->sql = "SELECT * FROM " . $table . " WHERE username = '" . $username . "'";
		$result = mysqli_query($this->connect, $this->sql);
		$row = mysqli_fetch_assoc($result);
		if (mysqli_num_rows($result) != 0) {
			$fullname = $row['fullname'];
			$email = $row['email'];
		} else {
			echo mysqli_error($this->connect);
			return "error";
		}
		$res = $fullname . "_" . $email;
		
		return $res;
	}

	function serviceBook($table, $username, $service)
	{
		$username = $this->prepareData($username);
		$service = $this->prepareData($service);
		$this->sql = "INSERT INTO " . $table . " (username, service) VALUES ('" . $username . "','" . $service . "')";
		if (mysqli_query($this->connect, $this->sql)) {
			return true;
		} else return false;
	}

	function book($table1, $table2, $table3, $username, $room_type_name, $check_in, $check_out)
	{
		$this->sql = "SELECT DISTINCT room_id FROM " . $table1 . " WHERE check_out > '" . $check_in. "'";
		$result = mysqli_query($this->connect, $this->sql) or die(mysqli_error($this->connect));

		$l1 = array();
		$i = 0;

		while($row = mysqli_fetch_row($result)) {
			$l1[$i++] = $row[0];
		}

		$this->sql = "SELECT room_id FROM " . $table2 . " WHERE room_type_name = '" . $room_type_name . "'";
		$result = mysqli_query($this->connect, $this->sql) or die(mysqli_error($this->connect));

		$l2 = array();
		$i = 0;
		while($row = mysqli_fetch_row($result)) {
			$l2[$i++] = $row[0];
		}

		$free_rooms = array_values(array_diff($l2, $l1));

		$res = "";

		if(sizeof($free_rooms) == 0) {
			$res = "error ";
			return $res;
		}

		$room_alloc = $free_rooms[0];
		$cost = 0;

		$this->sql = "SELECT cost FROM " . $table2 . " WHERE room_type_name = '" . $room_type_name . "'";
		$result = mysqli_query($this->connect, $this->sql) or die(mysqli_error($this->connect));
		$row = mysqli_fetch_assoc($result);

		if (mysqli_num_rows($result) != 0) {
			$cost = $row['cost'];
		}

		$this->sql = "INSERT INTO " . $table1 . " (room_id, check_in, check_out) VALUES ('" . $room_alloc . "', '" . $check_in . "', '" . $check_out . "')";
		$re = mysqli_query($this->connect, $this->sql);

		$last_id = mysqli_insert_id($this->connect);
		$this->sql = "INSERT INTO " . $table3 . " (username, b_id) VALUES ('" . $username . "', '" . $last_id . "')";

		if (mysqli_query($this->connect, $this->sql)) {
			$res = "success " . $room_alloc . " " . $cost;
		}
		
		return $res;
	}
}

?>

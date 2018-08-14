<?php
	require_once '../src/models/BaseModel.php';
	require_once '../src/models/ModelArea.php';

	class ModelUser extends BaseModel{

		public static function getTableName(){
			return 'users';
		}

		public static function getFields(){
			return array(
				"uid", "username", "password", "nama", "area_id", "role"
			);
		}

		public static function saveToDB($obj){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				static::saveObjToDB($obj, $db);
				$db->commit();
				$db = null;
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		public static function deleteFromDB($id){
			$str = static::generateSQLDelete("id=". $id);
			DB::executeSQL($str);
		}

		public static function retrieve($id){
			$obj = parent::retrieve($id);
			$obj->area =  ModelArea::retrieve($obj->area_id);
			return $obj;
		}

		public static function getUserLogin($user, $password, $role){
			$obj = DB::openQuery(
				'select a.*, b.uid as area_uid from users a inner join area b on a.area_id = b.id'
				.' where username = "'. $user.'"'
				.' and password = "'. $password.'"'
				.' and role = "'. $role.'"'
			);
			if (isset($obj[0])) {
				$user = $obj[0];
				$user->area =  ModelArea::retrieve($user->area_id);
				return $user;
			}
		}
	}

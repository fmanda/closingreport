<?php
	require_once '../src/models/BaseModel.php';

	class ModelProduct extends BaseModel{
		// public static function getTableName(){
		// 	return 'products';
		// }

		public static function getFields(){
			return array(
				"uid", "kode", "nama"
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
	}

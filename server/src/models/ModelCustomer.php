<?php
	require_once '../src/models/BaseModel.php';
	require_once '../src/models/ModelArea.php';

	class ModelCustomer extends BaseModel{

		// public static function getTableName(){
		// 	return 'customer';
		// }

		public static function getFields(){
			return array(
				"uid", "nama", "alamat", "ktpno", "name", "custphone", "area_id", "phone", "inetnumber", "lng", "lat"
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
	}

<?php
	require_once '../src/models/BaseModel.php';
	require_once '../src/models/ModelCustomer.php';
	require_once '../src/models/ModelArea.php';
	require_once '../src/models/ModelProduct.php';
	require_once '../src/models/ModelUser.php';
	
	class ModelOrder extends BaseModel{
		public static function getTableName(){
			return 'orders';
		}

		public static function getFields(){
			return array(
				"uid", "orderno", "tanggal", "customer_id", "phone", "inetnumber",
				"area_id", "product_id", "jenis_order", "user_id", "status"
			);
		}

		//override
		public static function retrieve($id){
			$obj = parent::retrieve($id);

			if (isset($obj->customer_id)) $obj->customer =  ModelCustomer::retrieve($obj->customer_id);
			if (isset($obj->area_id)) $obj->area =  ModelArea::retrieve($obj->area_id);
			if (isset($obj->product_id)) $obj->product =  ModelProduct::retrieve($obj->product_id);
			if (isset($obj->user_id)) $obj->user =  ModelUser::retrieve($obj->user_id);

			return $obj;
		}

		public static function deleteFromDB($id){
			$str = static::generateSQLDelete("id=". $id);
			DB::executeSQL($str);
		}

		public static function saveToDB($obj){
			$db = new DB();
			$db = $db->connect();
			$db->beginTransaction();
			try {
				static::saveObjToDB($obj, $db);
				static::updateCustomer($obj, $db);
				$db->commit();
				$db = null;
			} catch (Exception $e) {
				$db->rollback();
				throw $e;
			}
		}

		private static function updateCustomer($obj, $db){
			if ($obj->customer_id == 0){
				if (isset($obj->customer)) {
					$cust = $obj->customer;
					ModelCustomer::saveToDB($cust, $db);
					$obj->customer_id = $cust->id;
				}
			}
		}
	}

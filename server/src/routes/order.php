<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
require_once '../src/models/ModelOrder.php';
require_once '../src/classes/DB.php';

$app->get('/orderof/{teknisi_id}', function ($request, $response, $args) {
	try{
		$sql = "select a.*, b.uid as area_uid, c.uid as customer_uid, d.uid as product_uid
			from orders a
			inner join area b on a.area_id=b.id
			inner join customer c on a.customer_id=c.id
			inner join product d on a.product_id=d.id
			inner join users e on a.user_id=e.id
			where e.id = ". $args['teknisi_id'] ."
			and a.status not in('CLOSED','CANCEL')";
		$data = DB::openQUery($sql);
		return json_encode($data);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/order/{date1}/{date2}/{limit}/{page}/[{fieldname}/{keyword}]', function ($request, $response, $args) {
	try{
		$keyword = '';
		$fieldname = 'orderno';
		if (isset($args['keyword'])) $keyword = $args['keyword'];
		if (isset($args['fieldname'])) $fieldname = $args['fieldname'];

		$sql = "select a.*, b.nama as area, c.nama as customer, c.alamat, d.kode as product,
			e.nama as teknisi
			from orders a
			left join area b on a.area_id=b.id
			left join customer c on a.customer_id=c.id
			left join product d on a.product_id=d.id
			left join users e on a.user_id=e.id";

		if ($fieldname == "customer"){
			$fieldname = "c.nama";
		}else if ($fieldname == "alamat"){
			$fieldname = "c.alamat";
		}else if ($fieldname == "area"){
			$fieldname = "b.nama";
		}else if ($fieldname == "product"){
			$fieldname = "d.nama";
		}else if ($fieldname == "teknisi"){
			$fieldname = "e.nama";
		}else{
			$fieldname = "a.". $fieldname;
		}
		$sql = $sql ." where ".$fieldname." like '%" . $keyword ."%'";
		$sql = $sql . " and a.tanggal between '". $args['date1'] . "' and '" . $args['date2']. "'";

		$data = DB::paginateQuery($sql, $args['limit'], $args['page']);
		return json_encode($data);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->post('/order', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelOrder::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}

});

//retrieve
$app->get('/order/{id}', function ($request, $response, $args) {
	try{
		$obj = ModelOrder::retrieve($args['id']);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

//delete
$app->delete('/order/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelOrder::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

// $app->post('/orderfromclient', function ($request, $response) {
// 	$json = $request->getBody();
// 	$obj = json_decode($json);
// 	try{
// 		ModelOrder::prepareUpload($obj);
// 		$str = ModelOrder::saveToDB($obj);
// 		return json_encode($obj);
// 	}catch(Exception $e){
// 		$msg = $e->getMessage();
// 		return $response->withStatus(500)
// 			->withHeader('Content-Type', 'text/html')
// 			->write($msg);
// 	}
// });

<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
require_once '../src/models/ModelDetailOrder.php';
require_once '../src/classes/DB.php';

$app->get('/detailorder', function ($request, $response) {
	try{
		$list = ModelDetailOrder::retrieveList();
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/detailorder/{date1}/{date2}/{limit}/{page}/[{fieldname}/{keyword}]', function ($request, $response, $args) {
	try{
		$keyword = '';
		$fieldname = 'orderno';
		if (isset($args['keyword'])) $keyword = $args['keyword'];
		if (isset($args['fieldname'])) $fieldname = $args['fieldname'];

		$sql = "select a.*, b.orderno, c.nama as customer, c.alamat, d.nama as area, e.nama as teknisi
				from detailorder a
				inner join orders b on a.order_id = b.id
				inner join customer c on b.customer_id = b.id
				inner join area d on b.area_id = d.id
				inner join users e on a.user_id = e.id";

		if ($fieldname == "customer"){
			$fieldname = "c.nama";
		}else if ($fieldname == "alamat"){
			$fieldname = "c.alamat";
		}else if ($fieldname == "area"){
			$fieldname = "d.nama";
		}else if ($fieldname == "orderno"){
			$fieldname = "b.orderno";
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


$app->post('/detailorder', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelDetailOrder::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}

});

//retrieve
$app->get('/detailorder/{id}', function ($request, $response, $args) {
	try{
		$obj = ModelDetailOrder::retrieve($args['id']);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

//delete
$app->delete('/detailorder/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelDetailOrder::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->post('/detailorderclient', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		ModelDetailOrder::prepareUpload($obj);
		$str = ModelDetailOrder::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

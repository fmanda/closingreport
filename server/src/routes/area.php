<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require_once '../src/models/ModelArea.php';
require_once '../src/classes/DB.php';

//all list
$app->get('/area', function ($request, $response) {
	try{
		$list = ModelArea::retrieveList();
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

$app->get('/area/{limit}/{page}/[{fieldname}/{keyword}]', function ($request, $response, $args) {
	try{
		$keyword = '';
		$fieldname = 'nama';
		if (isset($args['keyword'])) $keyword = $args['keyword'];
		if (isset($args['fieldname'])) $fieldname = $args['fieldname'];

		$sql = "select * from area where ".$fieldname." like '%" . $keyword ."%'";

		$data = DB::paginateQuery($sql, $args['limit'], $args['page']);
		return json_encode($data);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


//insert/edit
$app->post('/area', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelArea::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}

});

//retrieve
$app->get('/area/{id}', function ($request, $response, $args) {
	$test = ModelArea::retrieve($args['id']);
	return json_encode($test);
});


//retrieve
// $app->get('/companybyuser/{user_name}/{password}', function ($request, $response, $args) {
// 	try{
// 		$user = ModelUser::getUserLogin($args['user_name'], $args['password']);
// 		if ($user == null){
// 			throw new Exception("[User] not found \n");
// 		}
// 		$company = ModelCompany::retrieve($user->company_id);
// 		if ($company == null){
// 			throw new Exception("Company not found \n");
// 		}
// 		return json_encode($company);
// 	}catch(Exception $e){
// 		$msg = $e->getMessage();
// 		return $response->withStatus(500)
// 			->withHeader('Content-Type', 'text/html')
// 			->write($msg);
// 	}
//
// });

//delete
$app->delete('/company/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelCompany::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

//units

$app->get('/unitsof/{id}', function ($request, $response) {
	$id = $request->getAttribute('id');
	try{
		$list = ModelUnits::retrieveList('company_id = ' . $id);
		return json_encode($list);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});


$app->post('/units', function ($request, $response) {
	$json = $request->getBody();
	$obj = json_decode($json);
	try{
		$str = ModelUnits::saveToDB($obj);
		return json_encode($obj);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}

});

//retrieve
$app->get('/units/{id}', function ($request, $response, $args) {
	$test = ModelUnits::retrieve($args['id']);
	return json_encode($test);
});

//delete
$app->delete('/units/{id}', function (Request $request, Response $response) {
	$id = $request->getAttribute('id');
	try{
		ModelUnits::deleteFromDB($id);
	}catch(Exception $e){
		$msg = $e->getMessage();
		return $response->withStatus(500)
			->withHeader('Content-Type', 'text/html')
			->write($msg);
	}
});

(: $Id: webapp/LiveContent/web/content.xql 1.38 2010/11/22 16:46:30EST tresea Exp  $ web/content.xql :)
xquery version "1.0";

import module namespace LiveContent-ContentBeGlobal="http://www.xyenterprise.com/LiveContent/xquery/contentbeglobal" at "../lib/contentBeGlobal.xql";
import module namespace LiveContent-Util="http://www.xyenterprise.com/LiveContent/xquery/util" at "../lib/util.xql";


declare namespace lbl_beglobal="java:hmac_encoder.LWAccess";
declare namespace lbl_logger="java:SimpleLog";

let $akshun := request:get-parameter("action",""),
$setxslmode := request:set-attribute("xslmode", request:get-parameter("xslmode", "")),

(: GLOBAL parameters that are usually available :)
$pub := request:get-parameter("pub", ""),						(: publication in use :)
$lang := request:get-parameter("lang", ""),						(: a language version of the publication :)
$type := request:get-parameter("type", "doctoc"),
$docid := request:get-parameter("docid", ""),
$filename := request:get-parameter("filename", ""),
$resource := request:get-parameter("resource", ""),
$query := request:get-parameter("query", ""),
$scope := request:get-parameter("scope", ""),
$tid := request:get-parameter("tid", ""),
$area := request:get-parameter("area", "live"),
$snippet := request:get-parameter("xml", "")

return

(: ======================================================= :)
(:				XML Viewing    					   		   :)
(: ======================================================= :)	
if ($akshun  eq "translate_me") then (

	let $page := LiveContent-ContentBeGlobal:translate_me($pub, $lang, $resource, $docid, $filename, $query, $scope, $tid),	
	$head := response:set-header("Content-Type", "text/html")
	
	(:	
	$logvar := util:log-system-err(concat("OUTER_BEGLOBAL_SERIALIZED: ", util:serialize($page, "method=xml"))),	
	$logvar := util:log-system-err(concat("OUTER_BEGLOBAL_SERIALIZED: ", "==END==DJAY==LASST"))
	:)
	
	return

	$page
	
) else if ($akshun  eq "snips") then (

	let $page := LiveContent-ContentBeGlobal:translate_snippet_eng_fr($snippet)	
	let $head := response:set-header("Content-Type", "text/html")
		
	return
		$page

) else if ($akshun  eq "snipsfr") then (

	let $page := LiveContent-ContentBeGlobal:translate_snippet_fr_eng($snippet)	
	let $head := response:set-header("Content-Type", "text/html")
		
	return
		$page
			
	
(: ======================================================= :)
(:				Unknown Action							   :)
(: ======================================================= :)

) else (
	(: === unknown action; returns xml :)
	let $xml := LiveContent-Util:unknown_action_xml($akshun, "pub"),
		$head := response:set-header("Content-Type", "application/xml")
	return
	
	$xml
)
(: the end :)

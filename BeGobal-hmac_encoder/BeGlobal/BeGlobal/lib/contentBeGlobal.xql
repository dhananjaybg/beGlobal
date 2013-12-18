(:  $Id: webapp/LiveContent/lib/content.xql 1.124 2010/11/29 11:41:39EST devries Exp  $  lib/content.xql :)
module namespace LiveContent-ContentBeGlobal="http://www.xyenterprise.com/LiveContent/xquery/contentbeglobal";

import module namespace LiveContent-Content="http://www.xyenterprise.com/LiveContent/xquery/content" at "../lib/content.xql";
import module namespace LiveContent-Pipeline="http://www.xyenterprise.com/LiveContent/xquery/pipeline" at "../lib/pipeline.xql";
import module namespace LiveContent-Util="http://www.xyenterprise.com/LiveContent/xquery/util" at "../lib/util.xql";
import module namespace LiveContent-UI="http://www.xyenterprise.com/LiveContent/xquery/ui" at "../lib/ui.xql";
import module namespace LiveContent-Pub="http://www.xyenterprise.com/LiveContent/xquery/pub" at "../lib/pub.xql";
import module namespace LiveContent-User="http://www.xyenterprise.com/LiveContent/xquery/user" at "../lib/user.xql";
import module namespace LiveContent-Audit="http://www.xyenterprise.com/LiveContent/xquery/audit" at "../lib/audit.xql";

(: Key-word in context :)
import module namespace kwic="http://exist-db.org/xquery/kwic";
declare namespace lc="http://www.sdlxysoft.com/LiveContent/lc";

declare namespace lbl_beglobal="java:hmac_encoder.LWAccess";
declare namespace lbl_logger="java:SimpleLog";


(: ******************************************************************* :)
(:	translate_snippet                                                  :)
(:  based on doc_html, used for BeGLobal translation                   :)
(: ******************************************************************* :)

declare function LiveContent-ContentBeGlobal:translate_snippet_fr_eng($filtered as xs:string)
{
	let $logvar := lbl_logger:write("=+=translate_me: passsed above888 ")
	(:
	let $logvar := lbl_logger:write($filtered)
:)
	let $beGlobal_translate_filtered := lbl_beglobal:TransFrench2English($filtered)
(:
	let $logvar := lbl_logger:write("=+=translate_me: RETURNED ")
:)	
	let $logvar := lbl_logger:write($beGlobal_translate_filtered)

	return
		$beGlobal_translate_filtered
};



(: ******************************************************************* :)
(:	translate_snippet                                                  :)
(:  based on doc_html, used for BeGLobal translation                   :)
(: ******************************************************************* :)

declare function LiveContent-ContentBeGlobal:translate_snippet_eng_fr($filtered as xs:string)
{
	let $logvar := lbl_logger:write("=+=translate_me: passsed above555 ")
	let $logvar := lbl_logger:write($filtered)

	let $beGlobal_translate_filtered := lbl_beglobal:TransEnglish2French($filtered)

	let $logvar := lbl_logger:write("=+=translate_me: RETURNED ")

	let $logvar := lbl_logger:write($beGlobal_translate_filtered)

	return
		$beGlobal_translate_filtered
};



(: ******************************************************************* :)
(:	translate_me                                                       :)
(:  based on doc_html, used for BeGLobal translation                   :)
(: ******************************************************************* :)
 

declare function LiveContent-ContentBeGlobal:translate_me($pub as xs:string, $lang as xs:string, $resource as xs:string, $docid as xs:string, $filename as xs:string, $query as xs:string, $scope as xs:string, $tid as xs:string) as node()
{
	(:
		-> finds an XML document ( via LC-Content:find_resource() )
			-> runs the XML document against a standard specific set of styles
			-> HOW... WHERE?
	:)
	
	
	let $auth := lcutil:is-authorized("Use application"),
		$vis  := local:pub_visible($pub, $lang),
		
		$logvar := lbl_logger:write("translate_me:In the  function")	
	
	return
	if(not($vis)) then (
		local:stream_node(LiveContent-UI:get_html("404notfound_content.html", "", <node/>, <node/>))
	) else if(not(LiveContent-Content:subscription_check($pub, $lang, $docid, "doc"))) then (
		local:stream_node(LiveContent-Content:subscription_reject($pub, $lang, $docid, "doc"))
	) else if ($auth) then (
		let $res := LiveContent-Content:find_resource($pub, $lang, "doc", $resource, $docid, $filename, "live")		
		
		return
		
		if($res eq "" or not(doc-available($res))) then (
			let $alt := "404notfound_content.html",
			$transid := util:uuid(),
			$enode := LiveContent-Content:audit_event("DocumentError", $transid, $pub, $lang, $docid, $tid, $scope, $query, ()),
			$logvar := lbl_logger:write("translate_me: Doc Not found")
			return
				local:stream_node(LiveContent-UI:get_html($alt, "", <node/>, <node/>))
		) else (

			  let $query_res := if($query  ne "")then (local:do_query($res, $docid, $query, $scope)) else (doc($res)),
			    $filtered := local:doc_html_filter($pub, $lang, doc($res), util:document-name($res)),
				$transid := util:uuid(),
				$logvar := lbl_logger:write("translate_me: Not_Cached_NOW"),
				$atype := if ($query eq "") then ("Document") else ("SearchResult"),
				$title := if(doc($res)/DOCUMENT/@title) then (doc($res)/DOCUMENT/@title) else ( attribute title {doc($res)/DOCUMENT/METADATA/title/text()} )
				let $atrail := LiveContent-Content:audit_event($atype, $transid, $pub, $lang, $docid, $tid, $scope, $query, ($title))
				let $parms :=
					<parameters>
					{
						let $draftconfig := LiveContent-Pub:get_config("draft", $pub, $lang)
						let $draftval :=
							if ($draftconfig/@value) then (
								xs:string($draftconfig/@value)
							) else ("no")
						return <param name="DRAFT" value="{$draftval}"/>
					}
					</parameters>				

				(: additional information required to fetch XForms:)
				let $user := xmldb:get-current-user(),
				$path := LiveContent-Pub:build_path($pub, $lang),
				$finalDocId := xs:string($filtered/@id),
				
				
				(:									
				$logvar := lbl_logger:write($filtered),												
				$logvar := util:log-system-err($filtered),	
				$logvar := util:log-system-err(concat("SERIALIZED: ", util:serialize($filtered, "method=xml"))),	
				:)
				
				$beGlobal_translate_filtered := lbl_beglobal:TransEnglish2French(util:serialize($filtered, "method=xml")),		
				(:
				$logvar := lbl_logger:write("============TRANSLATED_CONTENT_START==============="),
				$logvar := util:log-system-err($beGlobal_translate_filtered),
				$logvar := lbl_logger:write("============TRANSLATED_CONTENT_END==============="),
				:)
					
				$html := 
					transform:transform(
						<page>		
							{util:parse($beGlobal_translate_filtered)}
						</page>,
						LiveContent-UI:get_xsl(concat(xs:string(LiveContent-Pub:get_config("standard", $pub, "")/@value), "/html.xsl"), ""),
						LiveContent-Content:xsl_params($pub, $lang, util:document-name($res), $transid, <node/>)
					)				

				
				return
				$html
			
		)
	) else (
		local:stream_node(LiveContent-UI:deny_permission_xml("Use application", "doc_html"))
	)
}; 

(: ******************************************************************* :)
(:					SEARCH - HELPER FUNCTIONS 						   :)
(: COPIED AS IS FROM content.xql     NO CHANGES                        :)					
(: ******************************************************************* :)
declare function local:do_query($docpath as xs:string, $docid as xs:string, $query as xs:string, $scope as xs:string) as node()*
{
	let $node := 
	if($scope = "full" or $scope = "") then (
	        let $hit := ft:query(doc($docpath)/DOCUMENT, $query)
   		    let $expanded := util:expand($hit, "expand-xincludes=no")
				return $expanded
	) else (
		let $category := LiveContent-Content:get_categorical($scope),
			$finalAttr :=
				if(xs:string($category/@attribute) = "") then (".") else (xs:string($category/@attribute)),
			$eval := concat("collection('", util:collection-name($docpath), "')/DOCUMENT[@id = $docid]", $category/@xpath, "[ft:query(", $finalAttr, ", $query)]"),
			$nodes := util:eval($eval),
			$top := $nodes[1]/ancestor::DOCUMENT
		return
			util:expand($top, "expand-xincludes=no")
	)
	return

	(: insure that the document is returned, even if somehow the categorical search fails to find the target :)
	if(not($node)) then (
		doc($docpath)/DOCUMENT
	) else (
		$node
	)
};

(: ******************************************************************* :)
(:					VISIBLE FLAG - is a publication visible?  		
					-includes caching in the session				   :)
(: COPIED AS IS FROM content.xql     NO CHANGES                        :)					
(: ******************************************************************* :)
declare function local:pub_visible($pub as xs:string, $lang as xs:string) as xs:boolean
{
	let $pubstr := concat("LCPUB-VISIBLE-", $pub, "-", $lang),
		$auth := lcutil:is-authorized("Manage pubs")
	return
	
	(: 
		1]. if you have manage pubs permission, you can view any content
		2]. visible pub values are stored in your session so that we do not have to check every time
		3]. failed pub visible is checked every time you request (in case it becomes visible)
	:)
	
	if(session:get-attribute($pubstr) = "true" or $auth) then (
		true()
	) else (
		let $vis  := LiveContent-Pub:get_config("visible", $pub, $lang),
			$gvis := LiveContent-Pub:get_config("visible", $pub, "")
			return
			
			if($vis/@value and $gvis/@value) then (
				if(xs:string($vis/@value) = "visible" and xs:string($gvis/@value) = "visible") then (
					let $store := session:set-attribute($pubstr, "true")
					return
					true()
				) else ( false() )
			) else ( false() )
	)
};

(: ******************************************************************* :)
(:					PROFILING - HELPER FUNCTIONS 					   :)
(: ******************************************************************* :)
declare function local:stream_to_client($gzipnode as xs:base64Binary) as item()?
{
	let $req_encording := request:get-header("Accept-Encoding")
	return
		if ($req_encording and (contains($req_encording,"gzip") or contains($req_encording,"*"))) then (
	
			let $header := response:set-header("Content-Encoding", "gzip")
			return
				lcutil:stream-binary($gzipnode,"text/html")
		) else (
			lcutil:GZipToString($gzipnode)
		)
};

declare function local:stream_node($node as node()) as item()?
{
	local:stream_to_client(lcutil:StringToGZip(util:serialize($node, "method=html indent=yes" )))
};
declare function local:doc_html_filter($pub as xs:string, $lang as xs:string, $xml as node(), $docname as xs:string) as node()
{
	LiveContent-Content:filter($pub, $lang, $xml, $docname)
};
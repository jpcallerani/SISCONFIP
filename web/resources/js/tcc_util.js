var nvsMaxTabs = 6;
var nvsTabControlArray = new Array();

jQuery(document).ready(function(){	

    jQuery("#ControlTransition").ajaxStart(function(){
        jQuery("body").css("cursor", "wait");
        jQuery('#ControlTransition').fadeIn(200);
        jQuery('.tcc_progressbar_container').animate({
            bottom: '0px'
        }, 200); 
        flagAjaxStatus = true;
    });
    jQuery("#ControlTransition").ajaxStop(function(){
        jQuery("body").css("cursor", "default");
        jQuery('#ControlTransition').fadeOut(200);
        jQuery('.tcc_progressbar_container').animate({
            bottom: '-36px'
        }, 200); 
        flagAjaxStatus = false;
    });
});

function open_new_window(aURL, aWinName) {
	var wOpen;
	var sOptions;

	sOptions = 'status=yes,menubar=yes,scrollbars=yes,resizable=yes,toolbar=yes,location=0,modal=yes,alwaysRaised=yes';
	sOptions = sOptions + ',width=' + (screen.availWidth - 10).toString();
	sOptions = sOptions + ',height=' + (screen.availHeight - 122).toString();
	sOptions = sOptions + ',screenX=0,screenY=0,left=0,top=0';

	wOpen = window.open( '', aWinName, sOptions );
	wOpen.location = aURL;
	wOpen.focus();
	wOpen.moveTo( 0, 0 );
	wOpen.resizeTo( screen.availWidth, screen.availHeight );
	return wOpen;
}

		function nvsAddTabPosition(backingBean, title, idFunctionalityScreen, posFinal) {
			var notExistBackinBean = true;
			for (var i=0; i < nvsMaxTabs && notExistBackinBean; i++) {
				if (backingBean == nvsTabControlArray[i]) {
					notExistBackinBean = false;
				}
			}

			if (notExistBackinBean) {
				if (jQuery("#tabPrincipal").tabs("length") >= nvsMaxTabs + 1) {
					alert('Auuuuuuuuuuuuuuuuuuuuuuuuuu');
					return false;
				}
				//Vai inserir no final
				for (i=0; i < nvsMaxTabs; i++) {
					if (nvsTabControlArray[i] == null) {
						nvsTabControlArray[i] = backingBean;
						auxName = 'nvsDesktopTab' + (i + 1);
						jQuery("#tabPrincipal").append('<div id="' + auxName + '" style="display:none;"></div>');
						jQuery("#tabPrincipal").tabs('option', 'tabTemplate', '<li id="LI' + backingBean + '"><a href="#' + auxName + '" id="' + backingBean + 'href">' + title 
								+ '</a> <span class="ui-icon ui-icon-close" onclick="nvsRemovetab(\'' + backingBean + '\');">Remove Tab</span></li>');
						jQuery("#tabPrincipal").tabs('add' , '#' + auxName, backingBean);
						if (posFinal) {
							nvsEngineDesktopOpenTab(backingBean, 'nvsDesktopTabInitial' + (i + 1), idFunctionalityScreen, 'menu');
						} else {
							nvsEngineDesktopOpenTab(backingBean, 'nvsDesktopTabInitial' + (i + 1), idFunctionalityScreen, 'link');
						}
						break;
					}
				}
			} else {
				alert('fudeu');
			}
			return true; 
		}
		
function nvsRemovetab(backingBean) {
	index = jQuery('li', jQuery('#nvsDesktopTabs')).index(document.getElementById('LI' + backingBean));
	jQuery('#tabPrincipal').tabs('remove', index);
	for (var i=0; i < nvsMaxTabs; i++) {
		if (backingBean == nvsTabControlArray[i]) {
			nvsTabControlArray[i] = null;
			nvsEngineDesktopCloseTab(backingBean, 'nvsDesktopTabInitial' + (i + 1));
			break;
		}
	}
}
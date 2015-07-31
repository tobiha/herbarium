/**
 * INSPINIA - Responsive Admin Theme
 *
 */

/**
 * MainCtrl - controller
 */
function MainCtrl() {

    this.userName = 'Example user';
    this.helloText = 'Welcome in SeedProject';
    this.descriptionText = 'It is an application skeleton for a typical AngularJS web app. You can use it to quickly bootstrap your angular webapp projects and dev environment for these projects.';

};

function loginCtrl($rootScope, $scope, $http, $state) {
    var authenticate = function(credentials, callback) {

        var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('user', {headers : headers}).success(function(data) {
            if (data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).error(function() {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };

    authenticate();
    $scope.credentials = {};
    $scope.login = function() {
        authenticate($scope.credentials, function() {
            if ($rootScope.authenticated) {
                $state.go("index.collection");
                $scope.error = false;
            } else {
                $state.go("index.login");
                $scope.error = true;
            }
        });
    };
    $scope.logout = function() {
        $http.post('logout', {}).success(function() {
            $rootScope.authenticated = false;
            $state.go("index.main");
        }).error(function(data) {
            $rootScope.authenticated = false;
        });
    }

};


function searchCtrl($scope, $state, SearchService) {

	$scope.sh = {
		currentPage: 1,
		totalPages: 0,
		recordsPerPage: 25,
		showDetails: false

	};

	loadSheets(null, 1, $scope.sh.recordsPerPage);

	$scope.search = function (page) {

		var sname = $scope.herbarFilter.sname;
		var taxon = $scope.herbarFilter.taxon;
		var family = $scope.herbarFilter.family;
		var herbarNr = $scope.herbarFilter.herbarNr;
		var collector = $scope.herbarFilter.collector;

		console.log('search: sname=' + sname + ', taxon=' + taxon + ', family=' + family + ', herbarNr=' + herbarNr + ', collector=' + collector);
		loadSheets($scope.herbarFilter, 1, $scope.sh.recordsPerPage);

	};

	$scope.pages = function () {
		return _.range(1, $scope.sh.totalPages + 1);
	};

	$scope.goToPage = function (pageNumber) {
		if (pageNumber > 0 && pageNumber <= $scope.sh.totalPages) {
			$scope.sh.currentPage = pageNumber;
			loadSheets($scope.herbarFilter, pageNumber, $scope.sh.recordsPerPage);
		}
	};

	$scope.previous = function () {
		if ($scope.sh.currentPage > 1) {
			$scope.sh.currentPage-= 1;
			loadSheets($scope.herbarFilter, $scope.sh.currentPage, $scope.sh.recordsPerPage);
		}
	};

	$scope.next = function () {
		if ($scope.sh.currentPage < $scope.sh.totalPages) {
			$scope.sh.currentPage += 1;
			loadSheets($scope.herbarFilter, $scope.sh.currentPage, $scope.sh.recordsPerPage);
		}
	};

	$scope.entryFrom = function(){
		return (sh.currentPage * sh.recordsPerPage) - sh.recordsPerPage +1;
	};

	$scope.entryTo = function(){
		return (sh.currentPage * sh.recordsPerPage);
	};

	$scope.toggleDetails = function(sheet){
		 sheet.showDetails = !sheet.showDetails;
	};

	function loadSheets(herbarFilter, pageNr, recordsPerPage) {
		var filter = mapFilterToBackendDto(herbarFilter);
		var paging = mapPagingDto(pageNr, recordsPerPage);

		SearchService.getPagedSheets(paging, filter)
			.then(function(data) {

				$scope.sh.sheets = data.data;
				$scope.sh.currentPage = data.currentPage;
				$scope.sh.totalPages = data.maxPages;
				$scope.sh.totalRecords = data.totalRecords;
				$scope.sh.totalDisplayRecords = data.totalDisplayRecords;

				console.log("successfully loaded data");
			},
			function(errorMessage) {
				console.log("error occured" + errorMessage);
			}
		);

	}

	function mapFilterToBackendDto(herbarFilter) {
		if(herbarFilter == null){
			return {
				number: null,
				scientificName: null,
				family: null,
				subSpecies: null,
				collector: null
			}
		}

		return {
			number: herbarFilter.herbarNr,
			scientificName: herbarFilter.sname,
			family: herbarFilter.family,
			subSpecies: herbarFilter.subSpecies,
			collector: herbarFilter.collector
		};
	}

	function mapPagingDto(pageNr, recordsPerPage) {
		return {
			pageNr: pageNr,
			recordsPerPage: recordsPerPage
		};
	}

};

function detailsController($scope, $stateParams, $state, SearchService, SheetService) {
	$scope.sheet = $stateParams.sheet;
	$scope.dirtySheet = angular.copy($scope.sheet);

	$scope.findByNr = function(){

		if($scope.search.number != null){
			loadSheet($scope.search.number);
		}
	};

	$scope.updateSheet = function(){

		SheetService.saveOrUpdateSheet($scope.dirtySheet).then(function(){
				console.log("updated sheet with id: " + $scope.sheet.id);
				angular.copy($scope.dirtySheet, $scope.sheet);
				$state.go("index.details.view", $scope.sheet);
			},
			function (errorMessage) {
				//TODO show error
				console.log("error occured" + errorMessage);
			}
		)
	};

	$scope.reset = function(){
		angular.copy($scope.sheet, $scope.dirtySheet);
	};


	function loadSheet(number) {
		SearchService.getSheetByNr(number)
			.then(function (data) {
				$scope.sheet = data;
				$scope.dirtySheet = angular.copy($scope.sheet);
				console.log("successfully loaded sheet:" + $scope.sheet.number);
			},
			function (errorMessage) {
				//TODO show error
				console.log("error occured" + errorMessage);
			}
		);
	}

}

/**
 * GoogleMaps - Controller for data google maps
 */
function GoogleMaps($scope) {
	$scope.mapOptions = {
		zoom: 8,
		center: new google.maps.LatLng(35.240117, 24.8092691),
		// Style for Google Maps
		//styles: [{"featureType":"water","stylers":[{"saturation":43},{"lightness":-11},{"hue":"#0088ff"}]},{"featureType":"road","elementType":"geometry.fill","stylers":[{"hue":"#ff0000"},{"saturation":-100},{"lightness":99}]},{"featureType":"road","elementType":"geometry.stroke","stylers":[{"color":"#808080"},{"lightness":54}]},{"featureType":"landscape.man_made","elementType":"geometry.fill","stylers":[{"color":"#ece2d9"}]},{"featureType":"poi.park","elementType":"geometry.fill","stylers":[{"color":"#ccdca1"}]},{"featureType":"road","elementType":"labels.text.fill","stylers":[{"color":"#767676"}]},{"featureType":"road","elementType":"labels.text.stroke","stylers":[{"color":"#ffffff"}]},{"featureType":"poi","stylers":[{"visibility":"off"}]},{"featureType":"landscape.natural","elementType":"geometry.fill","stylers":[{"visibility":"on"},{"color":"#b8cb93"}]},{"featureType":"poi.park","stylers":[{"visibility":"on"}]},{"featureType":"poi.sports_complex","stylers":[{"visibility":"on"}]},{"featureType":"poi.medical","stylers":[{"visibility":"on"}]},{"featureType":"poi.business","stylers":[{"visibility":"simplified"}]}],
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};
}


angular
    .module('inspinia')
    .controller('MainCtrl', MainCtrl)
    .controller('loginCtrl', loginCtrl)
	  .controller('searchCtrl', searchCtrl)
	  .controller('detailsController', detailsController)
		.controller('GoogleMaps', GoogleMaps);
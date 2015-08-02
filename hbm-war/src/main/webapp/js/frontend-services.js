/**
 * Created by tobi on 27/07/15.
 */

angular.module('inspinia')
	.service('SearchService', ['$http', '$q', function($http, $q) {
		return {
			getPagedSheets: function (paging, filter) {
				var deferred = $q.defer();

				$http.post('/search/sheets', {
					paging: paging,
					filter: filter

				})
					.then(function (response) {
						if (response.status == 200) {
							deferred.resolve(response.data);
						}
						else {
							deferred.reject('Error retrieving paged sheets');
						}
					});
				return deferred.promise;
			},
			getSheetByNr: function (number) {
				var deferred = $q.defer();

				$http.get('/search/sheets/nr/' + number)
					.then(function (response) {
						if (response.status == 200) {
							deferred.resolve(response.data);
						}
						else {
							deferred.reject('Error retrieving sheet by number');
						}
					});
				return deferred.promise;
			}
		}
	}])
	.service('SheetService', ['$http', '$q', function($http, $q) {
		return {
			saveOrUpdateSheet: function(dirtySheet) {
				var deferred = $q.defer();

				$http({
					method: 'POST',
					url: '/sheet',
					data: dirtySheet,
					headers: {
						"Content-Type": "application/json",
						"Accept": "text/plain, application/json"
					}
				})
					.then(function (response) {
						if (response.status == 200) {
							deferred.resolve(response.data);
						}
						else {
							deferred.reject("Error saving sheet: " + response.data);
						}
					});

				return deferred.promise;
			}
		}
		}
	]);



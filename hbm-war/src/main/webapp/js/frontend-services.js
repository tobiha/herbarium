/**
 * Created by tobi on 27/07/15.
 */

angular.module('inspinia')
	.service('SearchService', ['$http', '$q', function($http, $q) {
		return {
			getPagedSheets: function(paging, filter){
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
			}
		}
	}

	]);

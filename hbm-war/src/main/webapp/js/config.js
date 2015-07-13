/**
 * INSPINIA - Responsive Admin Theme
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written state for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider) {
    $urlRouterProvider.otherwise("/index/main");
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
    });

    $stateProvider

        .state('index', {
            abstract: true,
            url: "/index",
            templateUrl: "views/public/common/content_top_navigation.html"
        })
        .state('index.login', {
            url: "/login",
            templateUrl: "views/public/login.html",
            data: { pageTitle: 'User login' }
        })
        .state('index.main', {
            url: "/main",
            templateUrl: "views/public/main.html",
            data: { pageTitle: 'Welcome' }
        })
			.state('index.secret', {
				url: "/secret",
				templateUrl: "views/secret.html",
				data: { pageTitle: 'Secret page' }
			})
}
angular
    .module('inspinia')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });

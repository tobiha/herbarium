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
        debug: true
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
          data: {pageTitle: 'User login'}
      })
      .state('index.main', {
          url: "/main",
          templateUrl: "views/public/main.html",
          data: {pageTitle: 'Welcome'}
      })
      .state('index.details', {
        url: "/details",
        abstract: true,
        templateUrl: "views/sheet_details.html",
        params: {
          sheet: null
        }

      })
      .state('index.details.view', {
        url: "/view",
        templateUrl: "views/sheet_details_view.html",
        data: {pageTitle: 'Details'},
        params: {
          sheet: null
        },
        resolve: {
          loadPlugin: function ($ocLazyLoad) {
            return $ocLazyLoad.load([
              {
                name: 'ui.event',
                files: ['js/plugins/uievents/event.js']
              },
              {
                name: 'ui.map',
                files: ['js/plugins/uimaps/ui-map.js']
              },
              {
                files: ['js/plugins/blueimp/jquery.blueimp-gallery.min.js','css/plugins/blueimp/css/blueimp-gallery.min.css']
              }
            ]);
          }
        }
      })
      .state('index.details.edit', {
        url: "/edit",
        templateUrl: "views/sheet_details_edit.html",
        data: {pageTitle: 'Details - Edit'},
        params: {
          sheet: null
        },
        resolve: {
          loadPlugin: function ($ocLazyLoad) {
            return $ocLazyLoad.load([
              {
                name: 'datePicker',
                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
              }
            ]);
          }
        }

      })
      .state('index.collection', {
          url: "/collection",
          templateUrl: "views/collection.html",
          data: {pageTitle: 'Collection'},
          resolve: {
          loadPlugin: function ($ocLazyLoad) {
            return $ocLazyLoad.load([
              {
                files: ['js/plugins/lodash/dist/lodash.js']
              },
              {
                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
              },
              {
                name: 'ui.footable',
                files: ['js/plugins/footable/angular-footable.js']
              }
            ]);
          }
        }
      })
      .state('index.map', {
        url: "/map",
        templateUrl: "views/map.html",
        data: { pageTitle: 'Map' }
    })
}
angular
    .module('inspinia')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });

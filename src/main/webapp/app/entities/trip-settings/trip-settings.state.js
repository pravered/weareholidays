(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trip-settings', {
            parent: 'entity',
            url: '/trip-settings?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripSettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-settings/trip-settings.html',
                    controller: 'TripSettingsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripSettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trip-settings-detail', {
            parent: 'trip-settings',
            url: '/trip-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripSettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-settings/trip-settings-detail.html',
                    controller: 'TripSettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripSettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TripSettings', function($stateParams, TripSettings) {
                    return TripSettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trip-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trip-settings-detail.edit', {
            parent: 'trip-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-settings/trip-settings-dialog.html',
                    controller: 'TripSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripSettings', function(TripSettings) {
                            return TripSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-settings.new', {
            parent: 'trip-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-settings/trip-settings-dialog.html',
                    controller: 'TripSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                location: null,
                                sync: null,
                                checkIn: null,
                                camerRoll: null,
                                cameraRollSyncTime: null,
                                facebookSyncTime: null,
                                twitterSinceId: null,
                                instagramSyncTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trip-settings', null, { reload: 'trip-settings' });
                }, function() {
                    $state.go('trip-settings');
                });
            }]
        })
        .state('trip-settings.edit', {
            parent: 'trip-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-settings/trip-settings-dialog.html',
                    controller: 'TripSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripSettings', function(TripSettings) {
                            return TripSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-settings', null, { reload: 'trip-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-settings.delete', {
            parent: 'trip-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-settings/trip-settings-delete-dialog.html',
                    controller: 'TripSettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TripSettings', function(TripSettings) {
                            return TripSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-settings', null, { reload: 'trip-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

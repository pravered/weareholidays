(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trip-summary', {
            parent: 'entity',
            url: '/trip-summary?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripSummary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-summary/trip-summaries.html',
                    controller: 'TripSummaryController',
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
                    $translatePartialLoader.addPart('tripSummary');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trip-summary-detail', {
            parent: 'trip-summary',
            url: '/trip-summary/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripSummary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-summary/trip-summary-detail.html',
                    controller: 'TripSummaryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripSummary');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TripSummary', function($stateParams, TripSummary) {
                    return TripSummary.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trip-summary',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trip-summary-detail.edit', {
            parent: 'trip-summary-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-summary/trip-summary-dialog.html',
                    controller: 'TripSummaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripSummary', function(TripSummary) {
                            return TripSummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-summary.new', {
            parent: 'trip-summary',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-summary/trip-summary-dialog.html',
                    controller: 'TripSummaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                twitter: null,
                                fb: null,
                                instagram: null,
                                photos: null,
                                publicPhotos: null,
                                notes: null,
                                videos: null,
                                checkIns: null,
                                distance: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trip-summary', null, { reload: 'trip-summary' });
                }, function() {
                    $state.go('trip-summary');
                });
            }]
        })
        .state('trip-summary.edit', {
            parent: 'trip-summary',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-summary/trip-summary-dialog.html',
                    controller: 'TripSummaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripSummary', function(TripSummary) {
                            return TripSummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-summary', null, { reload: 'trip-summary' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-summary.delete', {
            parent: 'trip-summary',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-summary/trip-summary-delete-dialog.html',
                    controller: 'TripSummaryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TripSummary', function(TripSummary) {
                            return TripSummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-summary', null, { reload: 'trip-summary' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('day-summary', {
            parent: 'entity',
            url: '/day-summary?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.daySummary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-summary/day-summaries.html',
                    controller: 'DaySummaryController',
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
                    $translatePartialLoader.addPart('daySummary');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('day-summary-detail', {
            parent: 'day-summary',
            url: '/day-summary/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.daySummary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/day-summary/day-summary-detail.html',
                    controller: 'DaySummaryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('daySummary');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DaySummary', function($stateParams, DaySummary) {
                    return DaySummary.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'day-summary',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('day-summary-detail.edit', {
            parent: 'day-summary-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-summary/day-summary-dialog.html',
                    controller: 'DaySummaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DaySummary', function(DaySummary) {
                            return DaySummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-summary.new', {
            parent: 'day-summary',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-summary/day-summary-dialog.html',
                    controller: 'DaySummaryDialogController',
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
                    $state.go('day-summary', null, { reload: 'day-summary' });
                }, function() {
                    $state.go('day-summary');
                });
            }]
        })
        .state('day-summary.edit', {
            parent: 'day-summary',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-summary/day-summary-dialog.html',
                    controller: 'DaySummaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DaySummary', function(DaySummary) {
                            return DaySummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-summary', null, { reload: 'day-summary' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('day-summary.delete', {
            parent: 'day-summary',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/day-summary/day-summary-delete-dialog.html',
                    controller: 'DaySummaryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DaySummary', function(DaySummary) {
                            return DaySummary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('day-summary', null, { reload: 'day-summary' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

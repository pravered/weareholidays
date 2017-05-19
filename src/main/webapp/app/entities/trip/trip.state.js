(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trip', {
            parent: 'entity',
            url: '/trip?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.trip.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip/trips.html',
                    controller: 'TripController',
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
                    $translatePartialLoader.addPart('trip');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trip-detail', {
            parent: 'trip',
            url: '/trip/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.trip.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip/trip-detail.html',
                    controller: 'TripDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trip');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Trip', function($stateParams, Trip) {
                    return Trip.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trip',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trip-detail.edit', {
            parent: 'trip-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-dialog.html',
                    controller: 'TripDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trip', function(Trip) {
                            return Trip.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip.new', {
            parent: 'trip',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-dialog.html',
                    controller: 'TripDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startLocationLat: null,
                                startLocationLong: null,
                                endLocationLat: null,
                                endLocationLong: null,
                                startTime: null,
                                endTime: null,
                                isUploaded: null,
                                isFinished: null,
                                isPublished: null,
                                publishedTime: null,
                                isDeleted: null,
                                featureImageLocalUri: null,
                                featureImageRemoteUri: null,
                                noOfDays: null,
                                createdAt: null,
                                viewCount: null,
                                secretKey: null,
                                featured: null,
                                isHidden: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: 'trip' });
                }, function() {
                    $state.go('trip');
                });
            }]
        })
        .state('trip.edit', {
            parent: 'trip',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-dialog.html',
                    controller: 'TripDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trip', function(Trip) {
                            return Trip.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: 'trip' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip.delete', {
            parent: 'trip',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip/trip-delete-dialog.html',
                    controller: 'TripDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trip', function(Trip) {
                            return Trip.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip', null, { reload: 'trip' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

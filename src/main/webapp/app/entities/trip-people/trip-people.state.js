(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trip-people', {
            parent: 'entity',
            url: '/trip-people?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripPeople.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-people/trip-people.html',
                    controller: 'TripPeopleController',
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
                    $translatePartialLoader.addPart('tripPeople');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trip-people-detail', {
            parent: 'trip-people',
            url: '/trip-people/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.tripPeople.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trip-people/trip-people-detail.html',
                    controller: 'TripPeopleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tripPeople');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TripPeople', function($stateParams, TripPeople) {
                    return TripPeople.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trip-people',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trip-people-detail.edit', {
            parent: 'trip-people-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-people/trip-people-dialog.html',
                    controller: 'TripPeopleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripPeople', function(TripPeople) {
                            return TripPeople.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-people.new', {
            parent: 'trip-people',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-people/trip-people-dialog.html',
                    controller: 'TripPeopleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                phoneBookType: null,
                                facebookType: null,
                                name: null,
                                email: null,
                                imageLocalUri: null,
                                imageRemoteUri: null,
                                type: null,
                                inTrip: null,
                                identifier: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trip-people', null, { reload: 'trip-people' });
                }, function() {
                    $state.go('trip-people');
                });
            }]
        })
        .state('trip-people.edit', {
            parent: 'trip-people',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-people/trip-people-dialog.html',
                    controller: 'TripPeopleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TripPeople', function(TripPeople) {
                            return TripPeople.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-people', null, { reload: 'trip-people' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trip-people.delete', {
            parent: 'trip-people',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trip-people/trip-people-delete-dialog.html',
                    controller: 'TripPeopleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TripPeople', function(TripPeople) {
                            return TripPeople.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trip-people', null, { reload: 'trip-people' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

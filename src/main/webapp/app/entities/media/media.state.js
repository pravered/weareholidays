(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('media', {
            parent: 'entity',
            url: '/media?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.media.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media.html',
                    controller: 'MediaController',
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
                    $translatePartialLoader.addPart('media');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('media-detail', {
            parent: 'media',
            url: '/media/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.media.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media-detail.html',
                    controller: 'MediaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('media');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Media', function($stateParams, Media) {
                    return Media.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'media',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('media-detail.edit', {
            parent: 'media-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.new', {
            parent: 'media',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contentLocalUrl: null,
                                contentRemoteUrl: null,
                                caption: null,
                                locationLat: null,
                                locationLong: null,
                                isPrivate: null,
                                address: null,
                                contentCreationTime: null,
                                contentSize: null,
                                mediaWidth: null,
                                mediaHeight: null,
                                thirdPartyId: null,
                                thirdPartyUrl: null,
                                mediaSource: null,
                                fetchingAddress: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('media');
                });
            }]
        })
        .state('media.edit', {
            parent: 'media',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.delete', {
            parent: 'media',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-delete-dialog.html',
                    controller: 'MediaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

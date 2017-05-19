(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('coupon', {
            parent: 'entity',
            url: '/coupon?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.coupon.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coupon/coupons.html',
                    controller: 'CouponController',
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
                    $translatePartialLoader.addPart('coupon');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('coupon-detail', {
            parent: 'coupon',
            url: '/coupon/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weareholidaysApp.coupon.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coupon/coupon-detail.html',
                    controller: 'CouponDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('coupon');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Coupon', function($stateParams, Coupon) {
                    return Coupon.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'coupon',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('coupon-detail.edit', {
            parent: 'coupon-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coupon/coupon-dialog.html',
                    controller: 'CouponDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coupon', function(Coupon) {
                            return Coupon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coupon.new', {
            parent: 'coupon',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coupon/coupon-dialog.html',
                    controller: 'CouponDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                objectId: null,
                                code: null,
                                message: null,
                                validFrom: null,
                                validTill: null,
                                isActive: null,
                                publishedMessage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('coupon', null, { reload: 'coupon' });
                }, function() {
                    $state.go('coupon');
                });
            }]
        })
        .state('coupon.edit', {
            parent: 'coupon',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coupon/coupon-dialog.html',
                    controller: 'CouponDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coupon', function(Coupon) {
                            return Coupon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coupon', null, { reload: 'coupon' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coupon.delete', {
            parent: 'coupon',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coupon/coupon-delete-dialog.html',
                    controller: 'CouponDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Coupon', function(Coupon) {
                            return Coupon.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coupon', null, { reload: 'coupon' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('CouponDetailController', CouponDetailController);

    CouponDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Coupon'];

    function CouponDetailController($scope, $rootScope, $stateParams, previousState, entity, Coupon) {
        var vm = this;

        vm.coupon = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('weareholidaysApp:couponUpdate', function(event, result) {
            vm.coupon = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

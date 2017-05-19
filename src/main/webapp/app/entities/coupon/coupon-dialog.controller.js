(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('CouponDialogController', CouponDialogController);

    CouponDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Coupon'];

    function CouponDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Coupon) {
        var vm = this;

        vm.coupon = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.coupon.id !== null) {
                Coupon.update(vm.coupon, onSaveSuccess, onSaveError);
            } else {
                Coupon.save(vm.coupon, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:couponUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

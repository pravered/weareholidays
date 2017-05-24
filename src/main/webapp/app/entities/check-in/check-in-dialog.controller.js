(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('CheckInDialogController', CheckInDialogController);

    CheckInDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CheckIn'];

    function CheckInDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CheckIn) {
        var vm = this;

        vm.checkIn = entity;
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
            if (vm.checkIn.id !== null) {
                CheckIn.update(vm.checkIn, onSaveSuccess, onSaveError);
            } else {
                CheckIn.save(vm.checkIn, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:checkInUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

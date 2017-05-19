(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSummaryDialogController', TripSummaryDialogController);

    TripSummaryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TripSummary'];

    function TripSummaryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TripSummary) {
        var vm = this;

        vm.tripSummary = entity;
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
            if (vm.tripSummary.id !== null) {
                TripSummary.update(vm.tripSummary, onSaveSuccess, onSaveError);
            } else {
                TripSummary.save(vm.tripSummary, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:tripSummaryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

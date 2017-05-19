(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('DaySummaryDialogController', DaySummaryDialogController);

    DaySummaryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DaySummary'];

    function DaySummaryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DaySummary) {
        var vm = this;

        vm.daySummary = entity;
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
            if (vm.daySummary.id !== null) {
                DaySummary.update(vm.daySummary, onSaveSuccess, onSaveError);
            } else {
                DaySummary.save(vm.daySummary, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:daySummaryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

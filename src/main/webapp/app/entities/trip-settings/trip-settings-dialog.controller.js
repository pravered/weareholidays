(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSettingsDialogController', TripSettingsDialogController);

    TripSettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TripSettings'];

    function TripSettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TripSettings) {
        var vm = this;

        vm.tripSettings = entity;
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
            if (vm.tripSettings.id !== null) {
                TripSettings.update(vm.tripSettings, onSaveSuccess, onSaveError);
            } else {
                TripSettings.save(vm.tripSettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:tripSettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripPeopleDialogController', TripPeopleDialogController);

    TripPeopleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TripPeople'];

    function TripPeopleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TripPeople) {
        var vm = this;

        vm.tripPeople = entity;
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
            if (vm.tripPeople.id !== null) {
                TripPeople.update(vm.tripPeople, onSaveSuccess, onSaveError);
            } else {
                TripPeople.save(vm.tripPeople, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:tripPeopleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

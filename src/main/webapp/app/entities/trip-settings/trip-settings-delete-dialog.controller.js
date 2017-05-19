(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSettingsDeleteController',TripSettingsDeleteController);

    TripSettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TripSettings'];

    function TripSettingsDeleteController($uibModalInstance, entity, TripSettings) {
        var vm = this;

        vm.tripSettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TripSettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

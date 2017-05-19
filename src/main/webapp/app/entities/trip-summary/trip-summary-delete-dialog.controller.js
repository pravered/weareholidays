(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripSummaryDeleteController',TripSummaryDeleteController);

    TripSummaryDeleteController.$inject = ['$uibModalInstance', 'entity', 'TripSummary'];

    function TripSummaryDeleteController($uibModalInstance, entity, TripSummary) {
        var vm = this;

        vm.tripSummary = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TripSummary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

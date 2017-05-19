(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('DaySummaryDeleteController',DaySummaryDeleteController);

    DaySummaryDeleteController.$inject = ['$uibModalInstance', 'entity', 'DaySummary'];

    function DaySummaryDeleteController($uibModalInstance, entity, DaySummary) {
        var vm = this;

        vm.daySummary = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DaySummary.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

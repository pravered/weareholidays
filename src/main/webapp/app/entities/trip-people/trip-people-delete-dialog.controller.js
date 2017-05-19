(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripPeopleDeleteController',TripPeopleDeleteController);

    TripPeopleDeleteController.$inject = ['$uibModalInstance', 'entity', 'TripPeople'];

    function TripPeopleDeleteController($uibModalInstance, entity, TripPeople) {
        var vm = this;

        vm.tripPeople = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TripPeople.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

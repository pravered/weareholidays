(function() {
    'use strict';

    angular
        .module('weareholidaysApp')
        .controller('TripDialogController', TripDialogController);

    TripDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Trip', 'TripSummary', 'TripSettings', 'Day', 'TripPeople', 'User', 'Coupon'];

    function TripDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Trip, TripSummary, TripSettings, Day, TripPeople, User, Coupon) {
        var vm = this;

        vm.trip = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tripsummaries = TripSummary.query({filter: 'trip-is-null'});
        $q.all([vm.trip.$promise, vm.tripsummaries.$promise]).then(function() {
            if (!vm.trip.tripSummaryId) {
                return $q.reject();
            }
            return TripSummary.get({id : vm.trip.tripSummaryId}).$promise;
        }).then(function(tripSummary) {
            vm.tripsummaries.push(tripSummary);
        });
        vm.tripsettings = TripSettings.query({filter: 'trip-is-null'});
        $q.all([vm.trip.$promise, vm.tripsettings.$promise]).then(function() {
            if (!vm.trip.tripSettingsId) {
                return $q.reject();
            }
            return TripSettings.get({id : vm.trip.tripSettingsId}).$promise;
        }).then(function(tripSettings) {
            vm.tripsettings.push(tripSettings);
        });
        vm.days = Day.query();
        vm.trippeople = TripPeople.query();
        vm.users = User.query();
        vm.coupons = Coupon.query({filter: 'trip-is-null'});
        $q.all([vm.trip.$promise, vm.coupons.$promise]).then(function() {
            if (!vm.trip.couponId) {
                return $q.reject();
            }
            return Coupon.get({id : vm.trip.couponId}).$promise;
        }).then(function(coupon) {
            vm.coupons.push(coupon);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trip.id !== null) {
                Trip.update(vm.trip, onSaveSuccess, onSaveError);
            } else {
                Trip.save(vm.trip, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('weareholidaysApp:tripUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

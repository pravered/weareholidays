'use strict';

describe('Controller Tests', function() {

    describe('Trip Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrip, MockTripSummary, MockTripSettings, MockDay, MockTripPeople, MockUser, MockCoupon;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrip = jasmine.createSpy('MockTrip');
            MockTripSummary = jasmine.createSpy('MockTripSummary');
            MockTripSettings = jasmine.createSpy('MockTripSettings');
            MockDay = jasmine.createSpy('MockDay');
            MockTripPeople = jasmine.createSpy('MockTripPeople');
            MockUser = jasmine.createSpy('MockUser');
            MockCoupon = jasmine.createSpy('MockCoupon');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Trip': MockTrip,
                'TripSummary': MockTripSummary,
                'TripSettings': MockTripSettings,
                'Day': MockDay,
                'TripPeople': MockTripPeople,
                'User': MockUser,
                'Coupon': MockCoupon
            };
            createController = function() {
                $injector.get('$controller')("TripDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'weareholidaysApp:tripUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

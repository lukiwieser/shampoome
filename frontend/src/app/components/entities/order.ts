import { BottleSize, DeliveryStatus } from "./enums";

export class Order {
    constructor(
        public nickName: String,
        public matriculationNumber: String,
        public shippingAddress: String,
        public price: Number,
        public bottleSize: BottleSize,
        public description: String,
        public ingredients: String,
        public isDelayed: Boolean,
        public status: DeliveryStatus,
        public processId: String
    ) {}
}
// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SprintwerkCapacitorAndroidHealthConnect",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "SprintwerkCapacitorAndroidHealthConnect",
            targets: ["AndroidHealthConnectPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "AndroidHealthConnectPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AndroidHealthConnectPlugin"),
        .testTarget(
            name: "AndroidHealthConnectPluginTests",
            dependencies: ["AndroidHealthConnectPlugin"],
            path: "ios/Tests/AndroidHealthConnectPluginTests")
    ]
)
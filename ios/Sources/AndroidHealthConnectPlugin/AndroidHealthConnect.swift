import Foundation

@objc public class AndroidHealthConnect: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
